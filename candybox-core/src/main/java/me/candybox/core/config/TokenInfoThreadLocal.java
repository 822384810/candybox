package me.candybox.core.config;
import me.candybox.core.vo.TokenInfoVO;

/** 
 * TokenInfo ThreadLocal
*/


public class TokenInfoThreadLocal {
    private static ThreadLocal<TokenInfoVO> threadLocal = new ThreadLocal<>();

    public static TokenInfoVO getTokenInfo() {
        return threadLocal.get();
    }

    public static void setTokenInfo(TokenInfoVO tokenInfoVO) {
        TokenInfoThreadLocal.threadLocal.set(tokenInfoVO);
    }

    public static void remove() {
        threadLocal.remove();
    }

}
