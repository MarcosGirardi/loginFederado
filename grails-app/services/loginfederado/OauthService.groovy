package loginfederado

import grails.transaction.Transactional
import groovy.json.JsonSlurper

@Transactional
class OauthService {

    def springSecurityService

    //Optencion del token de acceso de google
    def getGToken(def params) {

        def resp = ""

        //Armado de la url para solicitar el access_token
        def codigo = params.code
        def cliente = "895656730552-lc2llkq5ro647lmqoos2i29r5r73gfte.apps.googleusercontent.com"
        def secreto = "hWl_wGpKm7nck0ZPHKkzOG_n"
        def uri = "http://localhost:8080/Oauth/google"
        def grant = "authorization_code"
        String urlParameters =  "code=" + codigo + "&client_id=" + cliente + "&client_secret=" + secreto + "&redirect_uri=" + uri + "&grant_type=" + grant

        //Solicitud del access_token (POST)
        try {

            def url = new URL("https://accounts.google.com/o/oauth2/token?")
            def conn = url.openConnection()
            conn.setDoOutput(true)
            def writer = new OutputStreamWriter(conn.getOutputStream())

            writer.write(urlParameters)
            writer.flush()
            String line
            def reader = new BufferedReader(new     InputStreamReader(conn.getInputStream()))
            while ((line = reader.readLine()) != null) {
                resp = resp + line
            }
            writer.close()
            reader.close()

        }catch (Exception e){
            throw new Exception(e.getMessage())
        }

        //Conversion de la respuesta en una lista para sacar el access_token
        def list = new JsonSlurper().parseText(resp)

        if (list["access_token"]){
            return list["access_token"]
        } else {
            throw new Exception("no access_token recived")
        }

    }
    //------------------------------------------------------------------------------------------------------------------

    //Obtencion del gmail del usuario
    def getGmail(def token){

        def resp

        //Armado de la url para solicitar los datos del usuario autenticado (GET)
        def urlParameters = "access_token=" + token

        try {
            resp = new URL("https://www.googleapis.com/oauth2/v3/tokeninfo?" + urlParameters).getText()
        } catch (Exception e){
            throw new Exception(e.getMessage())
        }

        //Conversion de la respuesta en una lista para sacar el email
        def list = new JsonSlurper().parseText(resp)

        if (list["email"]){
            return list["email"]
        }else{
            throw new Exception("no email recived")
        }

    }
    //------------------------------------------------------------------------------------------------------------------

    //Logeo meidante email
    def login(def email){

        def usuario

        try {
            usuario = Usuario.findByEmail(email)
        }catch (Exception e){
            throw new Exception(e.getMessage())
        }

        if (usuario){
            springSecurityService.reauthenticate(usuario.username)
        }else {
            throw new Exception("usuario no encontrado")
        }

    }
    //------------------------------------------------------------------------------------------------------------------
}
