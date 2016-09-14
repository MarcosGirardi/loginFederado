class BootStrap {

    def initService

    def init = { servletContext ->
        log.println("iniciando")
        initService.init()
        log.println("inicio terminado")
    }
    def destroy = {
    }
}
