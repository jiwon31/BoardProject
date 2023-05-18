import { Link } from "react-router-dom";

export default function TabList({ tab }: { tab: string }) {
  const style =
    "w-full py-2.5 pl-2.5 pr-24 flex justify-start text-lg font-semibold hover:text-brand";

  return (
    <div className="flex flex-col space-y-1">
      <Link
        to="/mypage/profile"
        className={`${
          tab === "profile" && "text-brand border-l-2 border-brand"
        } ${style}`}
      >
        프로필 수정
      </Link>
      <Link
        to="/mypage/boards"
        className={`${
          tab === "boards" && "text-brand border-l-2 border-brand"
        } ${style}`}
      >
        내가 작성한 글
      </Link>
    </div>
  );
}
