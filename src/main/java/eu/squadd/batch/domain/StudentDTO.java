package eu.squadd.batch.domain;

import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Contains the information of a single student who has purchased
 * the course.
 *
 * @author smorcja
 */
@XmlRootElement(name="student")
public class StudentDTO {
    private String studentName;
    private String emailAddress;    
    private String purchasedPackage;

    public StudentDTO() {}

    public String getStudentName() {
        return studentName;
    }
    
    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPurchasedPackage() {
        return purchasedPackage;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPurchasedPackage(String purchasedPackage) {
        this.purchasedPackage = purchasedPackage;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("emailAddress", this.emailAddress)
                .append("name", this.studentName)
                .append("purchasedPackage", this.purchasedPackage)
                .toString();
    }
}
