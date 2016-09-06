package loginfederado

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured


@Transactional(readOnly = true)
class OAuthIDController {


    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def google() {

        log.println("ASDDSA")
        log.println("${params}")

        def code = "https://www.googleapis.com/oauth2/v1/userinfo?access_token="+params.code
        log.println(code)

        def respuesta = new URL("http://stackoverflow.com").getText()
        //log.println(respuesta)

        if (params.code){



        }


        //def resp = get "${code}"c
        //log.println(resp)

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
