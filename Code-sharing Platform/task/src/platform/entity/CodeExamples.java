
package platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "CodeExamples")
public class CodeExamples implements Comparable<CodeExamples> {

    @Id
    @JsonIgnore
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    @Column(name = "code")
    private String code;

    @Column(name = "date")
    private String date;

    @JsonIgnore
    @Column(name = "Creation_Date")
    private LocalDateTime creationDate;

    @JsonIgnore
    @Column(name = "expire_time")
    private LocalDateTime expireDate;

    @Column(name = "views")
    private int views;

    @JsonProperty(value = "time")
    @Column(name = "time")
    private int secondsToExpire;

    @JsonIgnore
    @Column(name = "restriction_type")
    private RestrictionType restrictionType;

    @JsonIgnore
    @Column(name = "never_expire")
    private boolean canNeverExpire;
    @Transient
    private String dateFormatter = "yyyy-MM-dd HH:mm:ss";
    @Transient
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormatter);

    public CodeExamples() {
    }

    public CodeExamples(String code) {
        this.code = code;
        this.date = LocalDateTime.now().format(dateTimeFormatter);
        this.creationDate = LocalDateTime.now();
        this.expireDate = LocalDateTime.of(2025, 12, 11, 11, 11);
        this.canNeverExpire = true;
        this.restrictionType = RestrictionType.NONE;
    }

    public CodeExamples(String code, int secondsToExpire, int views) {
        this.code = code;
        this.date = LocalDateTime.now().format(dateTimeFormatter);
        this.creationDate = LocalDateTime.now();
        this.expireDate = LocalDateTime.now().plusSeconds(secondsToExpire);

        if (views == 0) {
            this.restrictionType = RestrictionType.TIME;
        } else if (secondsToExpire == 0) {
            this.restrictionType = RestrictionType.VIEWS;
        } else {
            this.restrictionType = RestrictionType.BOTH;
        }

        this.views = views;
        this.secondsToExpire = secondsToExpire;
        this.canNeverExpire = false;
    }

//    public CodeExamples(String code, String date) {
//        this.code = code;
//        this.date = String.valueOf(date);
//    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    //    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean canNeverExpire() {
        return canNeverExpire;
    }

    public void setCanNeverExpire(boolean canNeverExpire) {
        this.canNeverExpire = canNeverExpire;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getSecondsToExpire() {
        return this.secondsToExpire;
    }

    public void setSecondsToExpire(int secondsToExpire) {
        this.secondsToExpire = secondsToExpire;
    }

    public RestrictionType getRestrictionType() {
        return restrictionType;
    }

    public void setRestrictionType(RestrictionType restrictionType) {
        this.restrictionType = restrictionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeExamples code1 = (CodeExamples) o;
        return Objects.equals(code, code1.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "Code{" +
                "code='" + code + '\'' +
                '}';
    }

    @Override
    public int compareTo(CodeExamples codeExamples) {
        return codeExamples.date.compareTo(this.date);
    }

    public enum RestrictionType {
        NONE,
        TIME,
        VIEWS,
        BOTH
    }
}


