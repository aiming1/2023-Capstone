import styles from "../styles/css/ContentDetail.module.css";
import React, { useState, useEffect, useParams } from "react";
import { useLocation } from "react-router";
import HeartButton from "./HeartButton";
import axios from "axios";

const ContentDetail = (props) => {
  const product = useLocation();
  const productId = product.state.id;
  //alert(product.state.id);
  const productMarket = product.state.market[0];
  //alert(productStore);
  const productImage = product.state.image;

  const [data, setData] = useState(null);
  const [heart, setHeart] = useState(false);

  const fetchData = async () => {
    setData(null);
    const response = await axios.get(
      `/api/product/${productId}/${productMarket}`
    );
    setData(response.data);
  };
  useEffect(() => {
    fetchData();
  }, []);

  //찜하기/해제
  //console.log({"name": data?.name});
  /*
  const addHeart = async () => {
    try {
      const productData={
        "id": productId,
                    "name": data?.name,
                    "image": data?.image,
                    "price": data?.price,
                    "market": productMarket,
                    "seller": data?.seller,
                    "updatedate": data?.updatedate,
                    "hearts": data?.hearts,
                    "details": data?.details,
                    "category": data?.category,
                    "producturl": data?.producturl
      };
      const response = await axios.post(`/api/product/${productId}/${productMarket}/heart/add`, productData);
      setHeart(!heart);
    } catch (e) {
      console.log({"error": e});
      //setError(e);
    }
  };

  useEffect(() => {
    addHeart();
  }, []);
*/
  const addHeart = async () => {
    const productData = {
      productId: data?.id,
      userId: data?.seller,
      productName: data?.name,
      market: data?.market,
      heartCheck: 1, //??
      date: data?.updatedate,
    };
    /*
    const productData={
            "id": productId,
                        "name": data?.name,
                        "image": data?.image,
                        "price": data?.price,
                        "market": productMarket,
                        "seller": data?.seller,
                        "updatedate": data?.updatedate,
                        "hearts": data?.hearts,
                        "details": data?.details,
                        "category": data?.category,
                        "producturl": data?.producturl
          };
*/
    console.log("data", productData);
    axios
      .get(`/api/product/${productId}/${productMarket}/heart/add`, productData)
      .then(function (response) {
        console.log("추가 성공", response);
        setHeart(!heart);
        alert("찜 성공");
        // response
      })
      .catch(function (error) {
        // 오류발생시 실행
        console.log("실패", error);
      })
      .then(function () {
        // 항상 실행
        console.log("데이터 요청 완료");
      });
  };

  //찜 해제
  const deleteHeart = async () => {
    axios
      .get(`/api/product/${productId}/${productMarket}/heart/delete`)
      .then(function (response) {
        console.log("삭제 성공", response);
        setHeart(!heart);
        alert("찜 해제");
      })
      .catch(function (error) {
        // 오류발생시 실행
        console.log("실패", error);
      })
      .then(function () {
        // 항상 실행
        console.log("데이터 요청 완료");
      });
  };

  /*
  const addHeart = async (e) => {
    setHeart(!heart);
  };
*/

    const lsts = localStorage.getItem("watched");
    useEffect(() => {
      let lst = JSON.parse(lsts);
      let dtail = [productId, productMarket, productImage];
      const isSame =JSON.stringify(lst).includes(JSON.stringify(dtail))
      if (!isSame){
        if (lst.length > 4) {
           lst.shift();
          }
         lst.push(dtail);
      lst = new Set(lst);
      lst = Array.from(lst);
      localStorage.setItem("watched", JSON.stringify(lst));
    }}, []);
    // 최근 본  상품.
    // detail 들어가면 product id를 watched에 추가

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
      {heart ? (
        <HeartButton heart={heart} onClick={deleteHeart}></HeartButton>
      ) : (
        <HeartButton heart={heart} onClick={addHeart}></HeartButton>
      )}

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
