/* 
 * Copyright (C) 2018 Antonio---https://github.com/AntonioBohne
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package chemistry.kiwiGUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Class that keeps track of interactions that need to edit other layouts 
 * they do not have access to. For example, this message loader is used to
 * connect the side menu bar with the main display. When a button is clicked
 * on it a message is sent to this class, the main controller detects a change
 * on the message and acts accordingly, modifiying the main display as needed.
 * @author https://github.com/AntonioBohne
 */
public class MessageLoader {
    
    /**
     * All possible messages that can be sent to the message queue.
     */
    public enum MESSAGE_TYPE{
        SHOW_ATOM_SCR,
        SHOW_MOLECULE_SCR,
        SHOW_LEWIS_SCR,
        SHOW_TEST_SCR,
        SHOW_SETTINGS_SCR,
        SHOW_PREVIEW_SCR,
        SEARCH_FLD_EVT;
    }
    
    /**
     * Wraps over the MessageType enum and adds a String list that can 
     * contain additional arguments the node may wish to send to the main
     * controller. This class is mainly used to control access to the 
     * message information. Outside classses only get access to the message 
     * information, they can not modifiy it without having to send a new message
     * trough well defined methods.
     */
    public class KiwiMessage{
        
        private MESSAGE_TYPE message;
        private List<String> arguments;
        
        /**
         * Sets the message type and any arguments of the message.
         * @param msg Message type. Signifies where this message came form.
         * @param varargs Optional arguments the main controller which processes
         * the messages may use.
         */
        private void setMessage(MESSAGE_TYPE msg, String... varargs){
            this.message = msg;
            this.arguments = new ArrayList<>(Arrays.asList(varargs));
        }
        
        /**
         * Returns the MESSAGE_TYPE of this KiwiMessage.
         * @return MESSAGE_TYPE which signifies wehre the message came from.
         */
        public MESSAGE_TYPE getMessageType(){
            return message;
        }
        
        /**
         * Returns a list with extra arguments the message may contain. <h3>
         * This list may be empty. Nodes are not forced to add arguments
         * to their messages.</h3>
         * @return List with extra arguments. May or may not be empty.
         */
        public List<String> getArguments(){
            return arguments;
        }
    }
    
    public static BooleanProperty MESSAGE_TRIGGER = new 
        SimpleBooleanProperty(false);
    
    /**
     * Keeps track of the last object that was clicked. 
     */
    private static KiwiMessage message = new MessageLoader().new KiwiMessage();
    
    /**
     * Sends a message to the message loader. Any controllers listening
     * for changes in the message will hear the message sent.
     * @param msg Message that will be sent.
     * @param varargs Aditional arguments that can be passed to the message.
     */
    public static void sendMessage(MESSAGE_TYPE msg, String ...varargs){
        message.setMessage(msg, varargs);
        MESSAGE_TRIGGER.set(!MESSAGE_TRIGGER.getValue());
    }
    
    /**
     * Returns the last message sent to the message qeue. 
     * @return Message class that contains message type and may or not 
     * contain aditional arguments.
     */
    public static KiwiMessage getLastMessage(){
        return message;
    }

}
