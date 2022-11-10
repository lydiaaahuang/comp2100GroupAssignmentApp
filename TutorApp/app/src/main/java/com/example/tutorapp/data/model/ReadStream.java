package com.example.tutorapp.data.model;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ReadStream {

    // TODO: the datastream will now have to simulate multiple activities (not only active status).
    // TODO: Create the required classes etc.

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args) {
        Timer timer = new Timer();
        List<Message> messages = readActivityFromStream("/Users/sungjaelee/Documents/2022/comp2100-group-assignment/TutorApp/app/src/main/assets/stream.csv");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (Message a : messages){
                    System.out.println(a.getChatID());
                    System.out.println(a.getSenderEmail());

                }
            }
        },0,5000);

    }

    /**
     * the following are the formation rules for a activity stream.
     *
     * ActivityStatus: [0]: ID, [1]: Status [2]: Time
     *
     * Student change and tutor change:
     * Only courses taught/desired courses should change.
     * This should be implemented in conjunction with the add course/remove course option.
     * (appending/removing from listArray)
     *
     * Traverse through the current courses array for a corresponding student and make
     * required changes.
     *
     * If the student appears in the stream .csv file, we assume that this is a change that they have made.
     * Thus detecting a change is not required. (assume a valid change)
     *
     */



    /**
     *
     * @param fileName the path to the CSV file.
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static List<Message> readActivityFromStream(String fileName){
        //TODO: determine if fileName should be final.
        List<Message> messages = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);
        try (BufferedReader bufferedReader = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)){
            String line = bufferedReader.readLine();

            while(line != null){ // get the features (columns) of the activity log.
                String[] details  = line.split(",");
                Message message = createMessage(details);
                messages.add(message);
                line = bufferedReader.readLine();
            }
            System.out.println(messages);
            return messages;

        }catch (IOException ioException){
            ioException.printStackTrace();
        }

        return messages;
    }

    public static Message createMessage(String[] metadata){
        String chatID  = metadata[0];
        String senderEmail = metadata[1];
        String receiverEmail = metadata[2];
        String message = metadata[3];

        return new Message(chatID,senderEmail,receiverEmail,message);

    }


}

