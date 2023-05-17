import { Link } from "react-router-dom";
import { BsClipboard2Heart } from "react-icons/bs";
import { Button } from "./ui/Button";
import SearchInput from "./SearchInput";
import useRecoilUser from "hooks/useRecoilUser";
import Avartar from "./Avartar";

export default function Header() {
  const { user } = useRecoilUser();

  return (
    <header className="flex justify-between items-center p-4 border-b border-gray-300">
      <Link to="/" className="flex items-center text-2xl">
        <BsClipboard2Heart className="text-brand" />
        <h1 className="ml-1 font-bold">Board</h1>
      </Link>
      <SearchInput />
      <div className="flex items-center gap-x-3">
        {user && (
          <Link to="/boards/new">
            <button className="px-4 py-2 text-gray-400 border border-gray-300 rounded-sm hover:border-brand hover:text-brand">
              글쓰기
            </button>
          </Link>
        )}
        {!user ? (
          <Link to="/login">
            <Button text="Login" />
          </Link>
        ) : (
          <Avartar user={user} />
        )}
      </div>
    </header>
  );
}
