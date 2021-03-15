****mini-chat using the UDP protocol in Java language****

Client message syntax:

+|username|    -    Register user. Server response is +|username|OK|

-|username|    -    Unregister user. Server response is -|username|OK|
 
?|username|    -    Show currently registered users. Available only for registered user. Server response is ?|username|username1 username2 ...|

!|username|message|    -    Send message. Available only for registered user. Server response is !|username|OK|

:|username|    -    Show messages. Available only for registered user. Server response is :|username|username1:message1:time1; username2:message2:time2 ...|OK|

