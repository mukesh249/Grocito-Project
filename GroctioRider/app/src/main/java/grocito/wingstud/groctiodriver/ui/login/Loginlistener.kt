package grocito.wingstud.groctiodriver.ui.login

import grocito.wingstud.groctiodriver.rest.responses.LoginResponse

interface Loginlistener {
    fun onStarted()
    fun onSuccess(loginResponse: LoginResponse?)
    fun onFailure(message : String)
}