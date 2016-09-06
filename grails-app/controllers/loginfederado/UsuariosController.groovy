package loginfederado

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured


@Transactional(readOnly = true)
class UsuariosController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Usuarios.list(params), model:[usuariosCount: Usuarios.count()]
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def show(Usuarios usuarios) {
        respond usuarios
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def create() {
        respond new Usuarios(params)
    }

    @Transactional
    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def save(Usuarios usuarios) {
        if (usuarios == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (usuarios.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond usuarios.errors, view:'create'
            return
        }

        usuarios.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'usuarios.label', default: 'Usuarios'), usuarios.id])
                redirect usuarios
            }
            '*' { respond usuarios, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def edit(Usuarios usuarios) {
        respond usuarios
    }

    @Transactional
    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def update(Usuarios usuarios) {
        if (usuarios == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (usuarios.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond usuarios.errors, view:'edit'
            return
        }

        usuarios.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'usuarios.label', default: 'Usuarios'), usuarios.id])
                redirect usuarios
            }
            '*'{ respond usuarios, [status: OK] }
        }
    }

    @Transactional
    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def delete(Usuarios usuarios) {

        if (usuarios == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        usuarios.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'usuarios.label', default: 'Usuarios'), usuarios.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuarios.label', default: 'Usuarios'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
