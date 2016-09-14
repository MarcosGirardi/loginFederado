package loginfederado

import grails.plugin.springsecurity.annotation.Secured

class OauthController {

    def oauthService

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def google() {

        def ok = true
        def Code = true
        def mensaje = ""
        def token
        def email

        if (params.code){
            try {
                token = oauthService.getGToken(params)
            }catch (Exception e){
                println e.getMessage()
                mensaje = e.getMessage()
                ok = false
            }
        }else {
            Code = false
        }

        if (ok){
            try {
                email = oauthService.getGmail(token)
            }catch (Exception e){
                mensaje = e.getMessage()
                ok = false
            }
        }

        if (ok){
            try {
                oauthService.login(email)
            }catch (Exception e){
                mensaje = e.getMessage()
                ok = false
            }
        }

        if (ok){
            mensaje = "logeo exitoso"
        }else {
        }

        if (Code){
            flash.message = message(code: "${mensaje}")
        }

        redirect controller: "usuario", action: "index", method: "GET"

    }
}
