import Kakao from "kakaojs"
// kakao.js
// Kakao API 키를 추가합니다.
Kakao.init("f7429e5a7e3c46efd999ac63b58ec9f1")

// 공유 기능 구현하기
function shareOnKakao(url, title, image) {
    Kakao.Link.sendDefault({
        objectType: "feed",
        content: {
            title: title,
            description: "Next.js 공유 테스트",
            imageUrl: image,
            link: {
                mobileWebUrl: url,
                webUrl: url,
            },
        },
        buttons: [
            {
                title: "Next.js 공유 테스트",
                link: {
                    mobileWebUrl: url,
                    webUrl: url,
                },
            },
        ],
    })
}

export default shareOnKakao
