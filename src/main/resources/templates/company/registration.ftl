<#import "base.ftl" as base>
<@base.layout>

    <form method="POST" action="registration" class="form-signin">
        <h2 class="form-signin-heading">Create your account</h2>

        <span>${error???then('has-error','')}</span>

        <div class="form-group ${error???then('has-error','')}">
            <input type="text" name="email" class="form-control" placeholder="Email"
                        autofocus="true">
        </div>

        <div class="form-group ${error???then('has-error','')}">
            <input type="password" name="password" class="form-control" placeholder="Password">
        </div>

        <div class="form-group ${error???then('has-error','')}">
            <input type="password" name="passwordConfirm" class="form-control"
                        placeholder="Confirm your password">
        </div>

        <div class="form-group ${error???then('has-error','')}">
            <input type="text" name="companyName" class="form-control" placeholder="Company name">
        </div>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Create account</button>
    </form>
</@base.layout>