package akwei.app;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "mch", url = "https://api.merchant.megaease.cn")
public interface MchService {

    @GetMapping("/v1/merchant/base/area-codes")
    List<AreaCode> getAllArea(@RequestParam(name = "parentAreaCodeId", defaultValue = "0") Long codeId);

}
