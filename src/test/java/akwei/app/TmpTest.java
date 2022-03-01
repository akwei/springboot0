package akwei.app;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TmpTest {

    @Test
    public void json() {
        String tlsKey = "-----BEGIN PRIVATE KEY-----\n" +
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCaHJX6zMOIpqUd\n" +
                "FIhfa9V4VchY6w6YZ8JjOf14/Ejgez5im+oBn2NlYiVmbGtAmqVTG7PG+yMyAGrp\n" +
                "eg3Sh1kUTMn+1CBkSHfNMEZSBfIwP3ZUdHlG8m6SgdwlTA5TekTMlPZYT68e37NK\n" +
                "Yaj2pe+wCmddQapNgbpbeMRo0TYcfb2EFK3Wfa0gjgN39FqsnVACzyihIdtXRqZR\n" +
                "1UzZOMGKp+3wKLNjAonFxa3ZqtcHop2gOySUsM/keVof9D8W5bJnxVbpBx2jt5Io\n" +
                "kfDRK6bNp3KWBBee4dWPYy4YHU5yJn54QttIHxysZ4XJAaKHmoMNHO5clxEigp3A\n" +
                "TzztQYozAgMBAAECggEAfpIVt7ruqM+88gpICp6k30UM28KJ5S/qndYl0HTbYcq2\n" +
                "rQRCh3yhhMW708gduhyIxZ4N49Gk0U84f3Ln4U2aIqsCh1EvHU3LXvFT/8+0Xl3w\n" +
                "0GIsrNE1cQxIgzVY2sSjtqZtQJCJUkEFnoRg4dnc5mdVB2mD9k4igTfJkBCrcW+l\n" +
                "6Riyo1ih5xlbtENuDcpo7hGhPgBFTpBccOM3IFR2W+cQifDjFvyifo8jvk6VRoQt\n" +
                "4wlrEUM6Mit2fPIDsoG3PT0ziVAGY0ORN8F17MLNgxBo0EyGkgFa9+SMRz9qkiXK\n" +
                "LXGPG6RyiLNcRO5rJfAXNb/w5HpTfUJ5dWzsM5tUmQKBgQDnOz3yTTHMZZhrKgmV\n" +
                "Cq91xjTZDCaPSpk3TtN4WALuObHggvSWRDma0XQgcVjQ0A3W+eSjYaLNzhgmUAO6\n" +
                "3Flbbj4Bzy5gUDqOXIBU//tT9QoLeL2GHWMvxBEPl7ojzHsUfKceTk6lRSHbB7fJ\n" +
                "pCCbctxYUPsvzI0yWVI15FnJJQKBgQCqnpbgsPFB9i6G7COxMIVrDfm8ozgX8R80\n" +
                "oPcl0Dd4+eNh58W7l1Xv1B3LYgCvv/AcenEhD19l5e9k3YQVKRdVtbVjKF+/ydc3\n" +
                "v0CiX/SeSfWKJ4JgrTfo04cGmlHCirDjBC9Y+gtzj0p5wrAxzGXwCmzrgH6/udtr\n" +
                "QpU9jovCdwKBgQC8WySt4LXlAwkymk6t6sNc07Uu7NfoN2Mxi3N1f3P7rGG6OdKV\n" +
                "KUQnWeEXoF0x05jCelMFx72/mddQNjOjnmTntpiqDqN/bzsTxkP/eXm4PU4OZC82\n" +
                "NXAbc8JWCkllaecEnDnh1PX5aKQYcGQEzD6Hgfv9sRG8MQr/bYUrQzTkRQKBgH/v\n" +
                "HGjspGp2YtrOc9Dn9hKYzxrwNB5XsbNvwwH6h63RifsepDiOOGT1Y0XefW9K94sT\n" +
                "EILemQS6DfBO3TxUrGebMaBhZLJVcWgeoyNd2flasZ9fErbVC6gr/AIqcoXyIsKG\n" +
                "n84S58VFu21jW2RrxD/iQVuw5Nl0Th+2NFuM6CTvAoGAJJKNq/siBHkg1MLcOcci\n" +
                "g5MEEUhh1NjspeP80ey9RvFtd0rjWZLPVP7N0RQDMMcYVwYRxQewvPYeKEtJovld\n" +
                "ZnGBealRRZ7QUpwDwVo2/tMWCor2wzVJzWotdbAfBdz+qyUjbNKra4yPCx7Rlf3J\n" +
                "FDXV6e2J5sERTcuyByC+Dhs=\n" +
                "-----END PRIVATE KEY-----";
        String tlsCert = "-----BEGIN CERTIFICATE-----\n" +
                "MIIC6TCCAdGgAwIBAgIGAX8hOEVqMA0GCSqGSIb3DQEBCwUAMCsxKTAnBgNVBAMM\n" +
                "IDYzYmVmNTNlMDdkNzRlMGFiNTcwMjI4MjI3MzFiNThhMB4XDTIyMDIyMjExMzY1\n" +
                "MFoXDTMyMDIyMDExMzY1MFowKzEpMCcGA1UEAwwgYjY5M2I3NTliMzIwNDQ0MDky\n" +
                "ZWRiM2NlYzMzYjM1MmMwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCa\n" +
                "HJX6zMOIpqUdFIhfa9V4VchY6w6YZ8JjOf14/Ejgez5im+oBn2NlYiVmbGtAmqVT\n" +
                "G7PG+yMyAGrpeg3Sh1kUTMn+1CBkSHfNMEZSBfIwP3ZUdHlG8m6SgdwlTA5TekTM\n" +
                "lPZYT68e37NKYaj2pe+wCmddQapNgbpbeMRo0TYcfb2EFK3Wfa0gjgN39FqsnVAC\n" +
                "zyihIdtXRqZR1UzZOMGKp+3wKLNjAonFxa3ZqtcHop2gOySUsM/keVof9D8W5bJn\n" +
                "xVbpBx2jt5IokfDRK6bNp3KWBBee4dWPYy4YHU5yJn54QttIHxysZ4XJAaKHmoMN\n" +
                "HO5clxEigp3ATzztQYozAgMBAAGjEzARMA8GA1UdEQQIMAaHBKG9NpUwDQYJKoZI\n" +
                "hvcNAQELBQADggEBAHReGkbG3eK8GRX2ki6hvB3FON0jmMaL3Rsp/ws9lkCXwvfj\n" +
                "ZB7KRwv/xCJmM3am/hk+/3i4ZMbeEe1JrPg46BWaIfkIxqr3s82Txca28WB/rUzh\n" +
                "hQ2yr60lkBYtpkfRC7ekAMhPPsk6yV488QYboh9XNlEOo3gBb4ryC8MHlWOHn0Ph\n" +
                "Ps9XBN4KurHqQjnL3F18zJrh7KPw+UScgp7zI0nzAR57KhL1BRreBzTHUHhS1Hto\n" +
                "gLGa3cZSWrMf3ovyg7LfeLg4s6fJQvUm40dcjbaie1uOIwa0GTXxB1XwhT7P2KyK\n" +
                "uQebYEeABgKy0wqNnczSiJaAyLdBxo5eoE6yjoE=\n" +
                "-----END CERTIFICATE-----";
        String tlsCaCert = "-----BEGIN CERTIFICATE-----\n" +
                "MIIC6TCCAdGgAwIBAgIGAX8grjztMA0GCSqGSIb3DQEBCwUAMCsxKTAnBgNVBAMM\n" +
                "IDYzYmVmNTNlMDdkNzRlMGFiNTcwMjI4MjI3MzFiNThhMB4XDTIyMDIyMjA5MDYw\n" +
                "NFoXDTMyMDIyMDA5MDYwNFowKzEpMCcGA1UEAwwgNjNiZWY1M2UwN2Q3NGUwYWI1\n" +
                "NzAyMjgyMjczMWI1OGEwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCB\n" +
                "FJ4kFEN70vT9ilUlfUQRDcNPvBeCtPU9E8eA1OaKjh8Y4wqxCwLTHmje1mWq0o9o\n" +
                "sye4Z876zfOeteuha6dwsehdC6gODNme/CWdGgtB1KIiJJ3fvVqcqvqQSpsaXIbu\n" +
                "5eCbmQcLt4R9fj3d9PMctz1QeC4B9vUTAZaekrDLVk4WpEEzBNDimIZfHRQq+PKu\n" +
                "R4fmDpxYdqzn/GLatGgVHmY35aAotskffRiVcp76xPD0q7c9hKnMxUagBK8gYPdC\n" +
                "dhENwaqOG/p9fsGsQB87UXsrMOJQi7bRT6UdB+iehwsZHzZwAYSwJXySe2/Z+H//\n" +
                "Zcp4mZRVM3yPaOX3ZHnBAgMBAAGjEzARMA8GA1UdEwEB/wQFMAMBAf8wDQYJKoZI\n" +
                "hvcNAQELBQADggEBAC0WiREWXfu+wZh390TCLwnCk/V2dGtcDpvcxm9T3Qf7aZAg\n" +
                "JhBAUZwib2LMtSuuWljTl4BE0IgPvyC3MvRxI6403cMG0w+tuVUwuXZLIyGtGcLL\n" +
                "P9jj6eHIrmTN/94cnxdZ8iFP+ia/uLcCDv8Rsx3I7cqqRJwwRD+Q8vNdr835P+Of\n" +
                "qAweLqYr2LD492H5/2It5yH54mNy2jiSqPTTn+N2sQCesu9+Lr0on4K98piUEVZ7\n" +
                "IBmnH8XR8DFEntN+iksoi+wtdweuiNc+2MCJu54qCaodQPdj/IMusFK1v84uCV7S\n" +
                "1iHrb7qjlU2nY+fNGgUc/VkPapv5Ky889Eu8Chs=\n" +
                "-----END CERTIFICATE-----";
        Map<String, Object> map = new HashMap<>();
        map.put("tlsKey", tlsKey);
        map.put("tlsCert", tlsCert);
        map.put("tlsCaCert", tlsCaCert);
        System.out.println(JsonUtil.toJson(map));
    }
}
