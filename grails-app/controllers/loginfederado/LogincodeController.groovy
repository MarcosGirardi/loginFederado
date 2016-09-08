package loginfederado

import grails.plugin.springsecurity.annotation.Secured

class LogincodeController {

    def springSecurityService

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def auth() {

        if (params.email){
            log.println("logeando a un " + params.email)
            def usuario = SecUser.findByEmail(params.email)
            if (usuario){
                springSecurityService.reauthenticate(usuario.username)
                log.println("logeo exitoso")
            } else{
                log.println("usuario inexistente")
            }
        } else{
            log.println("acceso no autorizado")
        }

        redirect (url: "/")
    }

}
