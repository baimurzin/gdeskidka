<#import "base.ftl" as base>
<@base.layout>
    <form method="POST" action="login" class="form-signin">
        <h2 class="form-heading">Log in</h2>

        <div class="form-group ${error???then('has-error','')}">
            <span>${errorMessage!}</span>
            <input name="username" type="text" class="form-control" placeholder="email"
                   autofocus="true"/>
            <input name="password" type="password" class="form-control" placeholder="Password"/>
            <span>${error!}</span>
            <#--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>-->

            <button class="btn btn-lg btn-primary btn-block" type="submit">Log In</button>
            <h4 class="text-center"><a href="/registration">Create an account</a></h4>
        </div>

    </form>
</@base.layout>