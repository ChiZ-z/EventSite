<#import "parts/common.ftl" as c>
<@c.page>
<div class="uk-container uk-container-center" style="padding-top: 50px">
    <form method="post" enctype="multipart/form-data">
        <fieldset class="uk-fieldset">
            <h1 class="uk-position-relative uk-position-center">${event.id}(${event.name})</h1>
            <div class="uk-margin">
                <input class="uk-input " name="tag" type="text" placeholder="${event.tag}" value="${event.tag}">
            </div>
            <div class="uk-margin">
                <input class="uk-input " name="nameEvent" type="text" placeholder="${event.name}" value="${event.name}">
            </div>
            <div class="uk-margin">
                <div uk-alert>How many people can come?<input class="uk-input " type="number" name="amount"
                                                              placeholder="${event.amountAll}"
                                                              value="${event.amountAll}"></div>
            </div>
            <div class="uk-margin">
                <input class="uk-textarea " rows="5" name="text" placeholder="${event.text}" value="${event.text}"/>
            </div>
            <#list artists as artist>
                <div>
                    <label><input class="uk-checkbox" type="checkbox"
                                  name="${artist}" ${event.artists?seq_contains(artist)?string("checked", "")} />${artist}
                    </label>
                </div>
            </#list>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div class="uk-margin">
                <button type="submit" class="uk-button uk-button-default">Submit</button>
            </div>
        </fieldset>
    </form>
</div>
</@c.page>