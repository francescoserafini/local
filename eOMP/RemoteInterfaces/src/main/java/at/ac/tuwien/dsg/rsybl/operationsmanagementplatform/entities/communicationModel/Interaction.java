/** 
   Copyright 2013 Technische Universitat Wien (TUW), Distributed SystemsGroup E184.               
   
   This work was partially supported by the European Commission in terms of the CELAR FP7 project (FP7-ICT-2011-8 #317790).
 
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

/**
 *  Author : Georgiana Copil - e.copil@dsg.tuwien.ac.at
 */
package at.ac.tuwien.dsg.rsybl.operationsmanagementplatform.entities.communicationModel;

import at.ac.tuwien.dsg.rsybl.operationsmanagementplatform.entities.interfaces.IInteraction;
import at.ac.tuwien.dsg.rsybl.operationsmanagementplatform.entities.interfaces.IMessage;
import at.ac.tuwien.dsg.rsybl.operationsmanagementplatform.entities.interfaces.IRole;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Georgiana
 */
public class Interaction implements Serializable, Comparable<Interaction>,IInteraction {
    private Long id;
    private String uuid;
    private String dialogUuid;
    private Date initiationDate;
    private IRole initiator;
    private IRole receiver;
    private Message message;
    private String type;
    @Override
    public int compareTo(Interaction o) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        if (o == this) {
            return EQUAL;
        }
        if (o.getInitiationDate().equals(this.getInitiationDate()) && o.getInitiator().getRoleName().equalsIgnoreCase(initiator.getRoleName())
                && o.getMessage().equals(message) && o.getReceiver().getRoleName().equalsIgnoreCase(receiver.getRoleName())) {
            return EQUAL;
        }
        if (this.getInitiationDate().before(o.getInitiationDate())) {
            return BEFORE;
        } else {
            return AFTER;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.initiationDate);
        hash = 59 * hash + Objects.hashCode(this.initiator);
        hash = 59 * hash + Objects.hashCode(this.receiver);
        hash = 59 * hash + Objects.hashCode(this.message);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object.getClass() != Interaction.class) {
            return false;
        }
        Interaction o = (Interaction) object;
        if (!o.getInitiationDate().equals(this.getInitiationDate()) || !o.getInitiator().getRoleName().equalsIgnoreCase(initiator.getRoleName())
                || !o.getMessage().equals(message) || !o.getReceiver().getRoleName().equalsIgnoreCase(receiver.getRoleName())) {
            return false;
        }
        return true;
    }

    /**
     * @return the initiationDate
     */
    public Date getInitiationDate() {
        return initiationDate;
    }

    /**
     * @param initiationDate the initiationDate to set
     */
    public void setInitiationDate(Date initiationDate) {
        this.initiationDate = initiationDate;
    }

    /**
     * @return the initiator
     */
    public IRole getInitiator() {
        return initiator;
    }

    /**
     * @param initiator the initiator to set
     */
    public void setInitiator(IRole initiator) {
        this.initiator = initiator;
    }

    /**
     * @return the receiver
     */
    public IRole getReceiver() {
        return receiver;
    }

    /**
     * @param receiver the receiver to set
     */
    public void setReceiver(IRole receiver) {
        this.receiver = receiver;
    }

    /**
     * @return the message
     */
    public IMessage getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(IMessage message) {
        this.message = (Message) message;
    }

    /**
     * @return the dialogUuid
     */
    public String getDialogUuid() {
        return dialogUuid;
    }

    /**
     * @param dialogId the dialogUuid to set
     */
    public void setDialogUuid(String dialogId) {
        this.dialogUuid = dialogId;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

}
