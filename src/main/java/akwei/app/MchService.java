package akwei.app;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "mch", url = "https://httpbin.org/")
public interface MchService {

    @GetMapping("/get")
    String getAllArea(@RequestParam(name = "parentAreaCodeId", defaultValue = "0") Long codeId,
                      @RequestHeader("customer") String customer
    );

}
