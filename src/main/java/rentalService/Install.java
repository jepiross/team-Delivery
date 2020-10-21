package rentalService;

import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Entity
@Table(name="Install_table")
public class Install {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long installId;
    private Integer qty;
    private String status;

    @PostPersist
    public void onPostPersist(){
        Installed installed = new Installed();
        BeanUtils.copyProperties(this, installed);
        installed.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        rentalService.external.Schedule schedule = new rentalService.external.Schedule();
        // mappings goes here
        BeanUtils.copyProperties(this, schedule);
        schedule.setInstallId(installId);
        schedule.setQty(qty);
        schedule.setStatus("SCHEDULED");
        DeliveryApplication.applicationContext.getBean(rentalService.external.ScheduleService.class)
            .installSchedule(schedule);

    }

    @PreRemove
    public void onPreRemove(){
        InstallCanceled installCanceled = new InstallCanceled();
        BeanUtils.copyProperties(this, installCanceled);
        installCanceled.publishAfterCommit();


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getInstallId() {
        return installId;
    }

    public void setInstallId(Long installId) {
        this.installId = installId;
    }
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
