import { User } from "types/user";
import { FaUserCircle } from "react-icons/fa";
import { FiChevronDown } from "react-icons/fi";
import { useState } from "react";
import { Link } from "react-router-dom";

export default function UserInfo({ user }: { user: User }) {
  const [isDropdown, setIsDropdown] = useState<boolean>(false);

  return (
    <div
      className="relative flex items-center justify-center gap-x-2 hover:cursor-pointer"
      onClick={() => setIsDropdown((prev) => !prev)}
    >
      <FaUserCircle className="text-2xl text-gray-500" />
      <span className="font-bold">{user.userName}</span>
      <FiChevronDown className="text-xl text-brand" />
      {isDropdown && (
        <div className="absolute top-10 flex flex-col items-center bg-zinc-100 rounded-md">
          <div className="flex items-center px-5 py-2 m-1 whitespace-nowrap rounded-md hover:bg-brand hover:text-white">
            <Link to="/mypage">마이페이지</Link>
          </div>
          <div className="flex items-center px-7 py-2 m-1 whitespace-nowrap rounded-md hover:bg-brand hover:text-white">
            <button>로그아웃</button>
          </div>
        </div>
      )}
    </div>
  );
}
