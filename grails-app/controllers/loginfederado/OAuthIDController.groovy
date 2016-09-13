package loginfederado

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured
import groovy.json.JsonSlurper

@Transactional(readOnly = true)
class OAuthIDController {


    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def google() {

        log.println("--------------------------ASDDSA---------------------------------")
        log.println("${params}")

        if (params.code){

    //Armado de la url para solicitar el access_token
            def codigo = params.code
            def cliente = "895656730552-lc2llkq5ro647lmqoos2i29r5r73gfte.apps.googleusercontent.com"
            def secreto = "hWl_wGpKm7nck0ZPHKkzOG_n"
            def uri = "http://localhost:8080/OAuthID/google"
            def grant = "authorization_code"

            String urlParameters =  "code=" + codigo + "&client_id=" + cliente + "&client_secret=" + secreto + "&redirect_uri=" + uri + "&grant_type=" + grant

    //Solicitud del access_token (POST)
            def url = new URL("https://accounts.google.com/o/oauth2/token?")
            def conn = url.openConnection()
            conn.setDoOutput(true)
            def writer = new OutputStreamWriter(conn.getOutputStream())

            writer.write(urlParameters)
            writer.flush()
            String line
            def resp = ""
            def reader = new BufferedReader(new     InputStreamReader(conn.getInputStream()))
            while ((line = reader.readLine()) != null) {
                    resp = resp + line
            }
            writer.close()
            reader.close()

    //Conversion de la respuesta en una lista para sacar el access_token
            def list = new JsonSlurper().parseText(resp)
            println list["access_token"]

    //Armado de la url para solicitar los datos del usuario autenticado (GET)
            def token = list["access_token"]

            urlParameters = "access_token=" + token
            resp = new URL("https://www.googleapis.com/oauth2/v3/tokeninfo?" + urlParameters).getText()

    //Conversion de la respuesta en una lista para sacar el email
            list = new JsonSlurper().parseText(resp)

            def mail = list["email"]
            println mail

        }

        redirect action: 'index'
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond OAuthID.list(params), model:[OAuthIDCount: OAuthID.count()]
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def show(OAuthID OAuthID) {
        respond OAuthID
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def create() {
        respond new OAuthID(params)
    }

    @Transactional
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def save(OAuthID OAuthID) {
        if (OAuthID == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (OAuthID.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond OAuthID.errors, view:'create'
            return
        }

        OAuthID.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'OAuthID.label', default: 'OAuthID'), OAuthID.id])
                redirect OAuthID
            }
            '*' { respond OAuthID, [status: CREATED] }
        }
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def edit(OAuthID OAuthID) {
        respond OAuthID
    }

    @Transactional
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def update(OAuthID OAuthID) {
        if (OAuthID == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (OAuthID.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond OAuthID.errors, view:'edit'
            return
        }

        OAuthID.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'OAuthID.label', default: 'OAuthID'), OAuthID.id])
                redirect OAuthID
            }
            '*'{ respond OAuthID, [status: OK] }
        }
    }

    @Transactional
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def delete(OAuthID OAuthID) {

        if (OAuthID == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        OAuthID.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'OAuthID.label', default: 'OAuthID'), OAuthID.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'OAuthID.label', default: 'OAuthID'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}