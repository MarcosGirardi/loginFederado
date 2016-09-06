package loginfederado

import grails.transaction.Transactional

@Transactional
class InitService {

    def init() {
        log.println("321")

        def userRole = SecRole.findByAuthority(Constants.USER_ROLE) ?: new SecRole(authority: Constants.USER_ROLE).save(failOnError: true)
        def adminRole = SecRole.findByAuthority(Constants.ADMIN_ROLE) ?: new SecRole(authority: Constants.ADMIN_ROLE).save(failOnError: true)

        def adminUser = SecUser.findByUsername('admin') ?: new SecUser(
                username: 'admin',
                password: 'admin',
                enabled: true).save(failOnError: true)

        if (!adminUser.authorities.contains(adminRole)) {
            SecUserSecRole.create adminUser, adminRole
        }

        def userUser = SecUser.findByUsername('user') ?: new SecUser(
                username: 'user',
                password: 'user',
                enabled: true).save(failOnError: true)

        if (!userUser.authorities.contains(userRole)) {
            SecUserSecRole.create userUser, userRole
        }

        def sudoUser = SecUser.findByUsername('sudo') ?: new SecUser(
                username: 'sudo',
                password: 'sudo',
                enabled: true).save(failOnError: true)

        if (!sudoUser.authorities.contains(adminRole)) {
            SecUserSecRole.create sudoUser, adminRole
        }
        if (!sudoUser.authorities.contains(userRole)) {
            SecUserSecRole.create sudoUser, userRole
        }

        def str = "marcos"
        def hashed1 = str.encodeAsMD5()
        def hashed2 = str.encodeAsMD5Bytes()
        def hashed3 = str.encodeAsSHA1()
        def hashed4 = str.encodeAsSHA1Bytes()
        log.println("String original: ${str}")
        log.println("MD5 hash:        ${hashed1}")
        log.println("MD5Bytes hash:   ${hashed2}")
        log.println("SHA-1 hash:      ${hashed3}")
        log.println("SHA-1Bytes hash: ${hashed4}")

        log.println("321")

    }

}
