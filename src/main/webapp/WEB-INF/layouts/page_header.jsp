<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>



<nav class="navbar navbar-toggleable-md navbar-expand-lg navbar-light bg-faded" style="background-color: #e3f2fd;">
  <div class="container">
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <a class="navbar-brand" href='<s:url value="/"></s:url>'>
      <img src='<s:url value="/resources/images/medicallab-logo.png"></s:url>' width="30" height="30" class="d-inline-block align-top" alt="logo">
      MedicalLab
    </a>

    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
      <div class="navbar-nav mr-auto">
        <a id="homeNavLink" class="nav-item nav-link" href='<s:url value="/"></s:url>'>Home</a>
        <sec:authorize access="hasRole('ADMIN')">
          <a id="usersNavLink" class="nav-item nav-link" href='<s:url value="/users"></s:url>'>Users</a>
        </sec:authorize>
        
        <sec:authorize access="hasRole('USER')">
	        <a id="patientsNavLink" class="nav-item nav-link" href='<s:url value="/patients"></s:url>'>Patients</a>
	        <a id="testsNavLink" class="nav-item nav-link" href='<s:url value="/tests"></s:url>'>Tests</a>
	        <a id="requestsNavLink" class="nav-item nav-link" href='<s:url value="/requests"></s:url>'>Requests</a>
        </sec:authorize>
      </div>
    
      
      <sec:authorize url="/">
         <sec:authentication property="principal.firstname" var="firstname" />
         <sec:authentication property="principal.lastname" var="lastname" />
         
         <%-- <sec:authentication property="principal.authorities" var="authorities" />
         <c:set var="joinedAuthorities">
	         <c:forEach var="authority" items="${authorities }">
	           ${authority }
	         </c:forEach>
         </c:set> --%>
                  
				<div class="navbar-nav">
					<a id="accountSettingsLink" class="nav-item nav-link mr-2" href='<s:url value="/me"></s:url>' data-toggle="tooltip" data-placement="bottom" title="${joinedAuthorities }">
					  <u>${firstname } ${lastname }</u> 
					  
					  <sec:authorize access="hasRole('USER') and hasRole('ADMIN')">
						  <i class="fa fa-user-secret" aria-hidden="true"></i>
					  </sec:authorize>
					  
					  <sec:authorize access="hasRole('USER') and hasRole('OFFICER')">
              <i class="fa fa-user-secret" aria-hidden="true"></i>
            </sec:authorize>
            
            <sec:authorize access="hasRole('USER') and hasRole('DOCTOR')">
              <i class="fa fa-user-secret" aria-hidden="true"></i>
            </sec:authorize>
            
            <sec:authorize access="hasRole('USER') and !hasRole('ADMIN') and !hasRole('OFFICER') and !hasRole('DOCTOR')">
              <i class="fa fa-user" aria-hidden="true"></i>
            </sec:authorize>
				  </a>
				  
					<a id="logoutLink" class="btn btn-outline-primary" href="#">Logout</a>
				</div>
      </sec:authorize>
    </div>
  </div>
</nav>

<c:url var="logoutUrl" value="/logout"></c:url>
<sf:form id="logoutForm" action="${logoutUrl }" method="post">
</sf:form>

<%-- ${requestScope['javax.servlet.forward.request_uri']} --%>

<%-- <form action="${logoutUrl }" method="post">
 <input type="submit" value="Log Out" />
 <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" />
</form> --%>