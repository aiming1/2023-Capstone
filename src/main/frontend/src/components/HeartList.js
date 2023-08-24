import React, { useState, useEffect } from "react";
import axios from "axios";
import styles from "../styles/css/ItemList.module.css";
import style from "../styles/css/ContentDetail.module.css";
import { ListItem } from "./ListItem";
import Pagination from "./Pagination";
import Loading from './Loading';

//글자수 제한 함수
const truncate = (str, n) => {
  return str?.length > n ? str.substr(0, n - 1) + "..." : str;
};

const HeartList = () => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const limit = 8; //페이지 당 최대 게시물 수
  const [page, setPage] = useState(1); //현재 페이지 번호
  //const offset = (page - 1) * limit; //페이지 당 첫 게시물 위치

  const indexOfLast = page * limit;
  const indexOfFirst = (page - 1) * limit;
  const currentPosts = (data) => {
    let currentPosts = 0;
    currentPosts = data.slice(indexOfFirst, indexOfLast);
    return currentPosts;
  };

  const fetchData = async () => {
    try {
      // 요청이 시작 할 때에는 error 와 users 를 초기화하고
      setError(null);
      setData(null);
      // loading 상태를 true 로 바꿉니다.
      setLoading(true);
      const response = await axios.get("/api/list");
      setData(response.data); // 데이터는 response.data 안에 들어있습니다.
      console.log("list: ", data);
    } catch (e) {
      setError(e);
      console.log("list: ", data);
    }
    setLoading(false);
  };

  useEffect(() => {
    fetchData();
  }, []);

  if (loading) return <div><Loading /></div>;
  if (error) return <div>에러가 발생했습니다</div>;
  if (!data) return null;

  return (
    <div className={style.divhome}>
      <div className={styles.itemlistcontent}>
        {currentPosts(data).map((item) => (
          <ListItem
            className={styles.listItem}
            key={item.url}
            id={item.id}
            market={item.market}
            price={item.price}
            name={truncate(item.name, 10)}
            image={item.image}
            hearts={item.hearts}
          />
        ))}
      </div>
      <div className={styles.pageMove}>
        <Pagination
          total={data.length}
          limit={limit}
          page={page}
          setPage={setPage}
        />
      </div>
    </div>
  );
};

export default HeartList;
