<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ attribute name="expensesTabIsActive" type="java.lang.Boolean" %>
<%@ attribute name="statisticsTabIsActive" type="java.lang.Boolean" %>
<%@ attribute name="isHomePage" type="java.lang.Boolean" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html lang="en" >
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.4.3/css/mdb.min.css">

    <script src="https://code.jquery.com/jquery-3.2.1.min.js" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.4.3/js/mdb.min.js"></script>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-12 col-md-8 push-md-2">
                <nav class="navbar navbar-toggleable-md navbar-inverse bg-primary">
                    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <a class="navbar-brand mr-5" href="<c:url value="/"/>">Joint Expense Tracker</a>
                    <c:if test="${not isHomePage}">
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav mr-auto">
                            <li class="nav-item ${expensesTabIsActive ? 'active' : ''}">
                                <a class="nav-link" href="<c:url value="/expense-group/${sessionScope.UUID}/expenses"/>">Expenses</a>
                            </li>
                            <li class="nav-item ${statisticsTabIsActive ? 'active' : ''}">
                                <a class="nav-link" href="<c:url value="/expense-group/${sessionScope.UUID}/statistics"/>">Statistics</a>
                            </li>
                        </ul>

                        <ul class="navbar-nav ml-auto">
                            <li class="nav-item">
                                <a class="btn btn-success" href="<c:url value="/expense-group/${sessionScope.UUID}/expenses/add"/>">Add expense</a>
                            </li>
                        </ul>
                    </div>
                    </c:if>
                </nav>
                <jsp:doBody/>
            </div>
        </div>
    </div>
</body>
</html>