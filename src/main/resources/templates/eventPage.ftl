<#import "parts/common.ftl" as c>
<#include "parts/Security.ftl">
<#assign dateCreate=time!"null"/>
<@c.page>
    <div class="tm-middle" xmlns="http://www.w3.org/1999/html">
        <div class="uk-grid" data-uk-grid-margin>
            <div class="uk-width-1-3" style="padding-right: 20px;border-right: 1px solid #c3c3c3;">

                <article class="uk-article">
                    <figure class="uk-overlay">
                        <h1 class="uk-article-title uk-float-left ">${event.name}</h1>


            <#if event.filename??>
            <img src="/img/${event.filename}" width="600" style="border-radius: 15px;"
                 class="card-img-top uk-margin-medium-bottom">
            </#if>
                            <#if event.confirm==false&&isAdmin>
                                 <form name="eventConfirm" action="/events/${event.id}/confirm" method="post">
                                     <button class="uk-button uk-button-default  ">confirm</button>
                                     <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                 </form>
                            </#if>

                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </figure>
                </article>
            </div>
            <div class="uk-width-2-3" style="padding-left: 50px;">
                <article class="uk-article">
                    <figure class="uk-overlay">
                        <table class="uk-table uk-table-middle uk-table-divider">
                            <thead>
                            <tr>
                                <th>Author Name</th>
                                <th>Tag</th>
                                <th>Guists</th>
                                <th>Personal</th>
                                <th>Description</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>${event.authorName}</td>
                                <td>${event.tag}</td>
                                <td><#list event.eventGuists as guist>${guist}  <#else >No Guists</#list></td>
                                <td><#list event.artists as artist>${artist}
                                <#else >
                                No Personal
                                </#list>
                                </td>
                                <td>${event.text}</td>
                            </tr>

                            </tbody>
                        </table>
                        <#if !event.isThereArePlaces()>
                        <div class="uk-position-relative uk-background-muted uk-position-center uk-animation-fade">
                            Sorry no place for you)
                        </div>
                        <#else >

                        </#if>
                        <#if !isEnabled>
                            <form name="eventPage2" action="/event/${event.id}" method="post">
                                <div class="uk-margin">
                                    <div uk-alert><h3>You didn't create Account!</h3>If you want to go to this event you
                                        can input your Email or Name <input class="uk-input " type="text"
                                                                            name="nameGuist"
                                                                            placeholder=""></div>
                                </div>
                                <div class="uk-position-relative uk-background-muted	uk-position-center uk-animation-fade">
                                    <button class="uk-button uk-button-default  ">I GO</button>
                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                </div>
                            </form>
                        <#else >
                        <div class="uk-margin">
                              <div class="uk-position-relative uk-background-muted	uk-position-center uk-animation-fade">
                                 <form name="eventPage2" action="/event/${event.id}" method="post">
                                     <button class="uk-button uk-button-default  ">I GO</button>
                                     <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                 </form>
                             </div>
                        </div>
                        </#if>
                    </figure>
                </article>
            </div>
        </div>
        <div class="uk-container uk-container-center" style="padding-top: 50px">

                                <#if isAdmin>
                                <div class="uk-position-relative uk-background-muted	uk-position-center uk-animation-fade">
                                    <a class="uk-button uk-button-text" href="/event/${event.id}/edit">edit</a>
                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                </div>

                                </#if>
 <#list SetAuthor as key>
<article class="uk-comment uk-comment-primary" style="border-bottom: solid 1px;border-color: #c3c3c3;">
    <header class="uk-comment-header uk-grid-medium uk-flex-middle" uk-grid>
        <div class="uk-width-auto">
            <img class="uk-comment-avatar" src="images/avatar.jpg" width="80" height="80" alt="">
        </div>
        <div class="uk-width-expand">
            <h4 class="uk-comment-title uk-margin-remove"><a class="uk-link-reset"
                                                             href="#">${key.getUserid().getUsername()}</a></h4>
            <ul class="uk-comment-meta uk-subnav uk-subnav-divider uk-margin-remove-top">
                <li>${key.getDate()}</li>
            </ul>
        </div>
    </header>
    <div class="uk-comment-body">
        <p>${key.getComment_value()}</p>
    </div>

</article>
 </#list>
            <form name="eventComment" action="/event/${event.id}/comment" method="post">
                <div class="uk-margin">
                    <textarea class="uk-textarea" rows="5" name="text" placeholder="Description"></textarea>
                </div>
                <button class="uk-button uk-button-default" type="submit">confirm</button>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            </form>
        </div>
    </div>

</@c.page>


