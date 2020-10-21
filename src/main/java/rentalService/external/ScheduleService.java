
package rentalService.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rentalService.Delivery;

@FeignClient(name="Schedule", url="${api.schedule.url}")
public interface ScheduleService {

    @RequestMapping(method= RequestMethod.POST, path="/schedules")
    public void installSchedule(@RequestBody Schedule schedule);

}
