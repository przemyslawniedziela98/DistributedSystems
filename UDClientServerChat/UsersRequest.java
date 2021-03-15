import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; 

public class UsersRequest 
{
    private List <String> userList = new ArrayList<>();
    private List <String> messagesList = new ArrayList<>();
    private String receviedData = "";

    public UsersRequest () {}
    
    public String processDataAndSendResponse(String receviedData)
    {
        this.receviedData = receviedData;
        String dataToSend = new String();
        if (isDataValid(receviedData))
        {
            String userName = receviedData.substring(receviedData.indexOf("|") + 1);
            userName = userName.substring(0,userName.indexOf("|"));
            if (receviedData.substring(0,1).equals("+"))
            {
                registerUser(userName);
                dataToSend = receviedData + "OK|";
            }
            else if (receviedData.substring(0,1).equals("-"))
            {
                unRegisterUser(userName);
                dataToSend = receviedData + "OK|";
            }
            else if (receviedData.substring(0,1).equals("?"))
            {
                if (isUserIsRegistered(userName)){dataToSend = receviedData + getCurrentUsers() + "|";}
                else dataToSend = "User not registered";
            }
            else if (receviedData.substring(0,1).equals("!"))
            {
                if (isUserIsRegistered(userName))
                {
                    saveMessages(userName, receviedData.substring(3+userName.length()));
                    dataToSend = "!|" +userName+ "|OK|";
                }
                else dataToSend = "User not registered";
            }
            else if (receviedData.substring(0,1).equals(":"))
            {
                if (isUserIsRegistered(userName)){dataToSend = ":|" +getMessages()+ "|OK|";}
                else dataToSend = "User not registered";
            }
        }
        else
        {
            dataToSend = "Data not valid";
        }
        return dataToSend;
    }

    public void registerUser (String User)
    {
        userList.add(User);
    }

    public void unRegisterUser (String User)
    {
        userList.remove(User);
    }

    private boolean isUserIsRegistered(String user)
    { 
        return userList.contains(user);
    }

    public String getCurrentUsers()
    {
        String currentUsers = "";
        for (int i=0; i<=userList.size()-1;i++)
        {  
            currentUsers+= userList.get(i).toString()+" ";
        }
        return currentUsers.substring(0, currentUsers.length() - 1);
    }

    private void saveMessages(String user, String message)
    {   
        LocalDateTime DateObj = LocalDateTime.now();
        DateTimeFormatter FormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = DateObj.format(FormatObj);
        messagesList.add(user + ":" + message.substring(0, message.length() - 1)+":"+ formattedDate);
    }

    public String getMessages()
    {
        String currentMessage = "";
        for (int i=0; i<=messagesList.size()-1;i++)
        {  
            currentMessage+= messagesList.get(i).toString()+"; ";
        }
        return currentMessage.substring(0, currentMessage.length() - 2);
    }
    private Boolean isDataValid (String receivedData)
    {
        Boolean validData;
        if ((receviedData.chars().filter(num -> num == '|').count() <2 && receviedData.chars().filter(num -> num == '|').count() >3)
                && receviedData.substring(receviedData.indexOf("|") + 1).length()<1 &&
                (!receviedData.substring(0,1).equals("+") || !receviedData.substring(0,1).equals("-") || !receviedData.substring(0,1).equals("?")
                || !receviedData.substring(0,1).equals("!")|| !receviedData.substring(0,1).equals(":")))
        {
            validData = false;
        }
        else
        {
            validData = true;
        }
        return validData;
    }
}
