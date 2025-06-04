<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<%
    // Retrieve the list of pending users set in the request by the servlet
    List<Map<String, String>> pendingUsers = (List<Map<String, String>>) request.getAttribute("pendingUsers");
%>
<center>
<h2>Pending User Approvals</h2>
<table border="1">
    <tr>
        <th>FirstName</th>
        <th>Email</th>
        <th>Actions</th>
    </tr>
    <%
        // Loop through the list of pending users and display them in the table
        for (Map<String, String> user : pendingUsers) {
    %>
    <tr>
        <td><%= user.get("firstname") %></td>
        <td><%= user.get("uemail") %></td>
        <td>
            <form action="Approval" method="post">
                <input type="hidden" name="id" value="<%= user.get("id") %>" />
               <input type="submit" name="action" value="Approval" />
               <input type="submit" name="action" value="reject" />

            </form>
        </td>
    </tr>
    <%
        }
    %>
</table>
</center>
