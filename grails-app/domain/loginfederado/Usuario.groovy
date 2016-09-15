package loginfederado

class Usuario extends SecUser{

    String email

    static constraints = {
        email(nullable:true)
    }
}
