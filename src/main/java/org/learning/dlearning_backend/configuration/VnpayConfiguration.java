package org.learning.dlearning_backend.configuration;

import lombok.Getter;
import org.learning.dlearning_backend.utils.VNPayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class VnpayConfiguration {
    @Getter
    @Value("${payment.vnPay.url}")
    private String vnp_PayUrl;
    @Value("${payment.vnPay.returnUrl}")
    private String vnp_ReturnUrl;
    @Value("${payment.vnPay.tmnCode}")
    private String vnp_TmnCode;
    @Getter
    @Value("${payment.vnPay.hashSecret}")
    private String secretKey;
    @Value("${payment.vnPay.version}")
    private String vnp_Version;
    @Value("${payment.vnPay.command}")
    private String vnp_Command;
    @Value("${payment.vnPay.order_type}")
    private String orderType;

    public Map<String, String> getVnPayConfig()
    {
        Map<String, String> vnPayParamsMap = new HashMap<>();
        vnPayParamsMap.put("vnp_Version", vnp_Version);
        vnPayParamsMap.put("vnp_Command", vnp_Command);
        vnPayParamsMap.put("vnp_TmnCode", vnp_TmnCode);
        vnPayParamsMap.put("vnp_Locale", "vn");
        vnPayParamsMap.put("vnp_CurrCode", "VND");
        vnPayParamsMap.put("vnp_TxnRef", VNPayUtils.getRandomNumber(8));
        vnPayParamsMap.put("vnp_OrderType", orderType);
        vnPayParamsMap.put("vnp_ReturnUrl", vnp_ReturnUrl);

        ZoneId vnZoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        ZonedDateTime now = ZonedDateTime.now(vnZoneId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        String vnp_CreateDate = now.format(formatter);
        vnPayParamsMap.put("vnp_CreateDate", vnp_CreateDate);

        String vnp_ExpireDate = now.plusMinutes(30).format(formatter);
        vnPayParamsMap.put("vnp_ExpireDate", vnp_ExpireDate);

        return vnPayParamsMap;
    }
}
