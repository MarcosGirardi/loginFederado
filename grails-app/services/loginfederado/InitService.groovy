package loginfederado

import grails.transaction.Transactional

@Transactional
class InitService {

    def init() {

        log.println("creando roles")
        def userRole = SecRole.findByAuthority("ROLE_USER") ?: new SecRole(authority: "ROLE_USER").save(failOnError: true)
        def adminRole = SecRole.findByAuthority("ROLE_ADMIN") ?: new SecRole(authority: "ROLE_ADMIN").save(failOnError: true)

//----------------------------------------------------------------------------------------------------------------------

        log.println("creando Admin")
        def adminUser = Usuario.findByUsername('admin') ?: new Usuario(
                username: 'admin',
                password: 'admin',
                email: 'admin@admin',
                enabled: true).save(failOnError: true)
        if (!adminUser.authorities.contains(adminRole)) {
            SecUserSecRole.create adminUser, adminRole
        }

        log.println("creando User")
        def userUser = Usuario.findByUsername('user') ?: new Usuario(
                username: 'user',
                password: 'user',
                email: 'user@user',
                enabled: true).save(failOnError: true)
        if (!userUser.authorities.contains(userRole)) {
            SecUserSecRole.create userUser, userRole
        }

        log.println("creando Sudo")
        def sudoUser = Usuario.findByUsername('sudo') ?: new Usuario(
                username: 'sudo',
                password: 'sudo',
                email: 'sudo@sudo',
                enabled: true).save(failOnError: true)
        if (!sudoUser.authorities.contains(adminRole)) {
            SecUserSecRole.create sudoUser, adminRole
        }
        if (!sudoUser.authorities.contains(userRole)) {
            SecUserSecRole.create sudoUser, userRole
        }

//----------------------------------------------------------------------------------------------------------------------

        log.println("Usuarios y roles creados")
    }
}
