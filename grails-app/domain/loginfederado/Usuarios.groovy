package loginfederado

class Usuarios extends SecUser{

    String nombre

    static hasMany = [oAuthIDs: OAuthID]

    static constraints = {
    nombre(nullable: true)
    }
}
