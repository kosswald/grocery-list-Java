"# grocery-list-Java" 

When sending a request for an email to the Java servlet, make an ajax request with name, email, and password fields.
These consistute the new user's informaiton (i.e., when a user signs up for the service, take their email, name, and password
and pass it to the servlet).

Something like this:
$.ajax({
    url:"EmailSender",
    data:{
      name: name,
      email: email,
      password: password
    },
    success: function(){
      console.log("email sent");
    }
})
