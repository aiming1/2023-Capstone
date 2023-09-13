import styles from "../../styles/css/Footer.module.css";

const Footer = () => {
  return (
    <div className={styles.rectangleParent}>
      <div className={styles.box}>
        <div className={styles.tel}>
          <span>
            팀 몽실몽실 | 서울캠퍼스 (03016) 서울시 종로구 홍지문 2길 20
            상명대학교 Tel : 02-2287-5114
          </span>
        </div>
        <div className={styles.notice}>
          모든 상품들에 대하여 중고플래닛은 통신판매중개자로서 거래 당사자가
          아니며 판매 회원과 구매 회원 간의 상품거래 정보 및 거래에 관여하지 않고
          어떠한 의무와 책임도 부담하지 않습니다.
        </div>
      </div>
    </div>
  );
};

export default Footer;
