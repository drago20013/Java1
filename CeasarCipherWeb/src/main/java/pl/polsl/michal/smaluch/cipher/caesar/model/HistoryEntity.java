package pl.polsl.michal.smaluch.cipher.caesar.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 *
 * @author Michal Smaluch
 * @version 1.0
 */
@Entity
@Table(name = "History")
public class HistoryEntity implements Serializable {
    private static final long serialVersionUID = 420L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private int shiftKey;
    private String message;
    private String processedMessage;
    private String operationType;
    private LocalDate entryDate;

    //TODO: Comments
    /**
     * Gets id
     * @return id
    */
    public int getId() {
        return id;
    }

    public int getShiftKey() {
        return shiftKey;
    }

    public String getMessage() {
        return message;
    }

    public String getProcessedMessage() {
        return processedMessage;
    }

    public String getOperationType() {
        return operationType;
    }

    public LocalDate getDate() {
        return entryDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setShiftKey(int key) {
        this.shiftKey = key;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setProcessedMessage(String processedMessage) {
        this.processedMessage = processedMessage;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public void setDate(LocalDate date) {
        this.entryDate = date;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof HistoryEntity)) {
            return false;
        }
        HistoryEntity other = (HistoryEntity) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }
}
