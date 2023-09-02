import React, { useState, useEffect } from "react";
import axios from "axios";
import styles from "../styles/css/ItemList.module.css";
import style from "../styles/css/ContentDetail.module.css";
import { ListItem } from "./ListItem";
import Pagination from "./Pagination";
import Loading from "./Loading";

//글자수 제한 함수
const truncate = (str, n) => {
  return str?.length > n ? str.substr(0, n - 1) + "..." : str;
};

const HeartList = () => {
  const [data, setData] = useState(null);
  const [heartListId, setHeartListId] = useState([]); //하트 유지

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  //페이징
  const [oneViewNumber, setOneViewNumber] = useState(8);
  const [page, setPage] = useState(1);
  const offset = (page - 1) * oneViewNumber;

  // 페이징 안의 페이징
  const [pageNationPage, setPageNationPage] = useState(1);
  const pageNationOffset = (pageNationPage - 1) * oneViewNumber;

  const fetchData = async () => {
    try {
      // 요청이 시작 할 때에는 error 와 users 를 초기화하고
      setError(null);
      setData(null);
      setHeartListId([]);
      // loading 상태를 true 로 바꿉니다.
      setLoading(true);
      const response = await axios.get("/api/list");
      setData(response.data); // 데이터는 response.data 안에 들어있습니다.
      //setHeartListId(makeArr(data)); //하트 유지
      //setHeartListId(heartData.map((list) => list.id)); //하트 유지
      console.log("list", data, "id list", heartListId);
    } catch (e) {
      setError(e);
      console.log("에러...", e); //
    }
    setLoading(false);
  };

  useEffect(() => {
    fetchData();
  }, []);

  if (loading)
    return (
      <div>
        <Loading />
      </div>
    );
  if (error) return <div>에러가 발생했습니다</div>;
  if (!data) return null;

  return (
    <div className={style.divhome}>
      <div className={styles.itemlistcontent}>
        {data.length > 0 &&
          data
            .slice(offset, offset + oneViewNumber)
            .map((item) => (
              <ListItem
                className={styles.listItem}
                key={item.url}
                id={item.id}
                market={item.market}
                price={item.price}
                name={truncate(item.name, 10)}
                image={item.image}
                hearts={item.hearts}
                data={data}
              />
            ))}
      </div>
      <div className={styles.pageMove}>
        <Pagination
          postLength={data.length}
          oneViewNumber={oneViewNumber}
          page={page}
          setPage={setPage}
          inMaxPageListNumber={5}
        />
      </div>
    </div>
  );
};

export default HeartList;
