import { useLocation, useNavigate } from "react-router-dom";
import { useMemo } from "react";

export default function useSearch() {
  const navigate = useNavigate();
  const { search } = useLocation();
  const searchParams = useMemo(() => new URLSearchParams(search), [search]);

  const setSearchParams = (option: string, value: string) => {
    cleanSearchParams(option);
    searchParams.set(option, value);
    navigate({
      pathname: "/",
      search: searchParams.toString(),
    });
  };

  // title, content, sort가 바뀌었을 때 page가 있다면 page를 없애준다.
  // 근데 title로 바뀌었을 때 content가 있다면 content를 없애주고
  // content로 바뀌었을 때 title이 있다면 title을 없애준다.
  function cleanSearchParams(searchOption: string) {
    if (searchOption === "title") {
      searchParams.delete("content");
      searchParams.delete("page");
    } else if (searchOption === "content") {
      searchParams.delete("title");
      searchParams.delete("page");
    }
  }

  return { searchParams, setSearchParams };
}
