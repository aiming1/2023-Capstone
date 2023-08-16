import styles from "../styles/css/ContentDetail.module.css";
import React, { useState, useEffect, useParams } from "react";
import { useLocation } from "react-router";
import HeartButton from "./HeartButton";
import axios from "axios";

let url = "https://web.joongna.com/"; //상품의 원글

const ContentDetail = (props) => {
  const product = useLocation();
  const productId = product.state.id;
  //alert(product.state.id);
  const productStore = product.state.store[0];
  //alert(productStore);

  const [data, setData] = useState(null);
  const [heart, setHeart] = useState(false);

  const fetchURLData = async () => {
    setData(null);
    //해당 상품 외부 링크 이동
    const response = await axios.get(`/api/product/${productId}/${productStore}`);
    setData(response.data);
    url = response.data;
    //console.log("url", url);
  };
  useEffect(() => {
    fetchURLData();
  }, []);

  //찜하기/해제 작동되는지 백이랑 통합 후 확인가능

  /*
  const fetchData = async () => {
    try {
      const response = await axios.get("/api/product/{itemId}/{market}/heart");
      setData(response.data); // 데이터는 response.data 안에 들어있습니다.
    } catch (e) {
      setError(e);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const toggleLike = async (e) => {
    const res = await axios.post("api/product/{itemId}/{market}/heart/add"); // 좋아요 누름 -> DB 갱신
    setHeart(!heart);
  };
  */
  const toggleLike = async (e) => {
    setHeart(!heart);
  };

  {
    /*
    const lsts = localStorage.getItem("watched");
    useEffect(() => {
      let lst = JSON.parse(lsts);
      let dtail = product.itemId;
      if (!lst.length === 5) {
        Array.shift();
      }
      lst.push(dtail);
      lst = new Set(lst);
      lst = Array.from(lst);
      localStorage.setItem("watched", JSON.stringify(lst));
    }, []);
    // 최근 본  상품.
    // detail 들어가면 product id를 watched에 추가
  */
  }

  return (
    <div className={styles.div}>
      <img className={styles.icon} alt="" src={data?.image} />
      <div className={styles.parent}>
        <b className={styles.title}>{data?.name}</b>
        <b className={styles.price}>{data?.price}원</b>
        <div className={styles.category}>{`홈 > 여성의류 > 신발`}</div>
        <div className={styles.name}>{data?.seller}</div>
        <div className={styles.date}>5분 전</div>
        <div className={styles.views}>조회 20000</div>
        <div className={styles.heart}>찜 {data?.hearts}</div>
      </div>
      <HeartButton heart={heart} onClick={toggleLike}></HeartButton>

      <div
        className={styles.btn_golink}
        onClick={() => {
          window.open(data?.producturl);
        }}
      >
        <div className={styles.child} />
        <div className={styles.div7}>보러 가기</div>
      </div>

      <div className={styles.div8}>{data?.details}</div>
      <div className={styles.div9}>
        <p className={styles.p}>거래거래</p>
        <p className={styles.p}>대충 거래글 끝</p>
      </div>
      <div className={styles.line}></div>
      <div className={styles.group}>
        <img className={styles.icon5} alt="" src={data?.image} />
        <div className={styles.div12}>
          <div className={styles.inner} />
          <img className={styles.div13} alt="" src="/img/right-side.svg" />
        </div>
        <div className={styles.div14}>
          <div className={styles.inner} />
          <img className={styles.div15} alt="" src="/img/left-side.svg" />
        </div>
      </div>
      <div className={styles.container}>
        <div className={styles.div20}>중고나라</div>
        <div className={styles.div21}>
          <img
            className={styles.icon6}
            alt=""
            src="/img/중고나라 아이콘 1.png"
          />
        </div>
      </div>
    </div>
  );
};

export default ContentDetail;
