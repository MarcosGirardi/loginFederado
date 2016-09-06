/*
def appName = grails.util.Metadata.current.'app.name'
def baseURL = grails.serverURL ?: "http://127.0.0.1:${System.getProperty('server.port', '8080')}/${appName}"
oauth {
    // ...
    providers {
        // ...

        // Choose only one "google" block

        // for Google OAuth 1.0 DEPRECATED from Google
        // it uses api class provided from Scribe library
        google {
            api = org.scribe.builder.api.GoogleApi
            key = 'oauth_google_key'
            secret = 'oauth_google_secret'
            successUri = '/oauth/google/success'
            failureUri = '/oauth/google/error'
            callback = "${baseURL}/oauth/google/callback"
            scope = 'https://www.googleapis.com/auth/userinfo.email'
        }

        // for Google OAuth 2.0
        google {
            api = org.grails.plugin.springsecurity.oauth.GoogleApi20
            key = 'oauth_google_key'
            secret = 'oauth_google_secret'
            successUri = '/oauth/google/success'
            failureUri = '/oauth/google/error'
            callback = "${baseURL}/oauth/google/callback"
            scope = 'https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email'
        }
        // ...
    }
}
*/

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'loginfederado.SecUser'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'loginfederado.SecUserSecRole'
grails.plugin.springsecurity.authority.className = 'loginfederado.SecRole'
grails.plugin.springsecurity.logout.postOnly = false
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	[pattern: '/',               access: ['permitAll']],
	[pattern: '/error',          access: ['permitAll']],
	[pattern: '/index',          access: ['permitAll']],
	[pattern: '/index.gsp',      access: ['permitAll']],
	[pattern: '/shutdown',       access: ['permitAll']],
	[pattern: '/assets/**',      access: ['permitAll']],
	[pattern: '/**/js/**',       access: ['permitAll']],
	[pattern: '/**/css/**',      access: ['permitAll']],
	[pattern: '/**/images/**',   access: ['permitAll']],
	[pattern: '/**/favicon.ico', access: ['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
	[pattern: '/assets/**',      filters: 'none'],
	[pattern: '/**/js/**',       filters: 'none'],
	[pattern: '/**/css/**',      filters: 'none'],
	[pattern: '/**/images/**',   filters: 'none'],
	[pattern: '/**/favicon.ico', filters: 'none'],
	[pattern: '/**',             filters: 'JOINED_FILTERS']
]



// Added by the Spring Security OAuth2 Google Plugin:
grails.plugin.springsecurity.oauth2.domainClass = 'loginfederado.OAuthID'
