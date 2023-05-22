import useSearch from "hooks/useSearch";
import { useState, useEffect } from "react";
import { AiOutlineSearch } from "react-icons/ai";

export default function SearchInput() {
  const [searchOption, setSearchOption] = useState<string>("title");
  const [searchText, setSearchText] = useState<string>("");
  const { searchParams, setSearchParams } = useSearch();

  const handleOptionChange = (e: React.ChangeEvent<HTMLSelectElement>) =>
    setSearchOption(e.target.value);

  const handleTextChange = (e: React.ChangeEvent<HTMLInputElement>) =>
    setSearchText(e.target.value);

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const text = searchText.trim();
    if (!text) {
      alert("검색어를 입력해주세요");
      return;
    }
    setSearchParams(searchOption, searchText);
  };

  useEffect(() => {
    if (!searchParams.toString()) {
      setSearchOption("title");
      setSearchText("");
    }
    searchParams.forEach((value, key) => {
      setSearchText(value);
      setSearchOption(key);
    });
  }, [searchParams]);

  return (
    <div className="w-7/12 flex items-center justify-center gap-x-2 border border-gray-300">
      <select
        className="w-1/5 p-3 border-r border-gray-300 outline-none"
        value={searchOption}
        onChange={handleOptionChange}
      >
        <option value="title">제목</option>
        <option value="content">본문</option>
      </select>
      <form className="w-full flex items-center" onSubmit={handleSubmit}>
        <input
          className="w-full border-none outline-none"
          value={searchText}
          placeholder="Search..."
          onChange={handleTextChange}
        />
        <button className="bg-brand p-3">
          <AiOutlineSearch className="text-2xl text-white" />
        </button>
      </form>
    </div>
  );
}
