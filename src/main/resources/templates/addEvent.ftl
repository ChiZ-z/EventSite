<#import "parts/common.ftl" as c>
<#assign error1=event1!"null"/>
<#assign error2=event2!"null"/>
<#assign error3=event3!"null"/>
<#assign error4=event4!"null"/>
<#assign error5=event5!"null"/>
<@c.page>
    ${event?ifExists}
 <div class="uk-container uk-container-center" style="padding-top: 50px">
     <form method="post" enctype="multipart/form-data">
         <fieldset class="uk-fieldset">
             <h1 class="uk-position-relative uk-position-center">New Event</h1>

             <div class="uk-margin">
                 <input class="uk-input <#if error4=="4">uk-form-danger</#if>" name="tag" type="text" placeholder="Tag">
             </div>

             <div class="uk-margin">
                 <input class="uk-input <#if error1=="1">uk-form-danger</#if>" name="nameEvent" type="text" placeholder="Name of Event">
             </div>
             <div class="uk-margin" >

                 <div uk-alert>How many people can come?<input class="uk-input <#if error2=="2">uk-form-danger</#if>" type="number" name="amount" placeholder="1"></div>
             </div>
             <form class="uk-form">
                 <input type="date" name="date">
             </form>
             <div class="uk-margin">
                 <textarea class="uk-textarea <#if error3=="3">uk-form-danger</#if>" rows="5" name="text" placeholder="Description"></textarea>
             </div>

             <div class="uk-margin"  uk-margin>
                 <div uk-form-custom="target: true">
                     <input type="file" name="file">
                     <input class="uk-input uk-form-width-medium" type="text" placeholder="Select file" disabled>
                 </div>
                 <button class="uk-button uk-button-default  <#if error5=="5">uk-button-danger</#if>">Submit</button>
             </div>
             <input type="hidden" name="_csrf" value="${_csrf.token}"/>
             <div class="uk-margin">
                 <button type="submit" class="uk-button uk-button-default">Добавить</button>
             </div>

         </fieldset>

     </form>
 </div>
</@c.page>