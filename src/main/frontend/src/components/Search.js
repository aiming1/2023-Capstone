import React, { useState, useEffect } from "react";
import axios from "axios";
import styles from "../styles/css/ItemList.module.css";
import { ListItem } from "./ListItem";
import { useParams } from "react-router";
import Pagination from "./Pagination";
import Loading from "./Loading";

//글자수 제한 함수
const truncate = (str, n) => {
  return str?.length > n ? str.substr(0, n - 1) + "..." : str;
};

const Search = () => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const limit = 8; //페이지 당 최대 게시물 수
  const params = useParams();

  //페이징
  const [oneViewNumber, setOneViewNumber] = useState(20);
  const [page, setPage] = useState(1);
  const offset = (page - 1) * oneViewNumber;

  /*
  한 페이지에 출력할 물건 개수 조절하려면 이부분 주석 해제하고
  62번쩨 줄에 currentPosts(data).map~으로 수정하면 돼요!!
  일단 주석처리 해놨습니당..
  const indexOfLast = page * limit;
    const indexOfFirst = (page - 1) * limit;
    const currentPosts = (data) => {
        let currentPosts = 0;
        currentPosts = data.slice(indexOfFirst, indexOfLast);
        //console.log({"current post: ": currentPosts, "indexofFirst": indexOfFirst, "indexofLast": indexOfLast})
        return currentPosts;
      }; */

  const fetchData = async () => {
    try {
      // 요청이 시작 할 때에는 error 와 users 를 초기화하고
      setError(null);
      setData(null);
      // loading 상태를 true 로 바꿉니다.
      setLoading(true);
      const response = await axios.get(
        "/api/search/" + params.word + "/" + page
      );
      console.log(response.data);
      setData(response.data); // 데이터는 response.data 안에 들어있습니다.
    } catch (e) {
      setError(e);
    }
    setLoading(false);
  };

  useEffect(() => {
    fetchData();
  }, [page]);

  if (loading)
    return (
      <div>
        <Loading />
      </div>
    );
  if (error) return <div>에러가 발생했습니다</div>;
  if (!data) return null;
  return (
    <div>
      <div className={styles.itemlistcontent}>
        {data.map((item) => (
          <ListItem
            className={styles.listItem}
            key={item.name}
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

export default Search;
