import { Button } from "components/ui/Button";
import useAuth from "hooks/useAuth";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { LoginRequest } from "types/auth";

const initialLoginInfo: LoginRequest = {
  email: "",
  password: "",
};

export default function Login() {
  const [loginInfo, setLoginInfo] = useState<LoginRequest>(initialLoginInfo);
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    login.mutate(loginInfo, {
      onSuccess: () => navigate("/"),
      onError: (error) => alert(error.message),
    });
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setLoginInfo((prev) => ({ ...prev, [name]: value }));
  };

  return (
    <section className="flex flex-col items-center gap-y-6 py-10 max-w-5xl mx-auto">
      <h1 className="text-2xl font-bold">로그인</h1>
      <form
        className="flex flex-col gap-y-3 w-7/12 py-10"
        onSubmit={handleSubmit}
      >
        <div className="flex flex-col gap-y-3 mb-10">
          <input
            type="email"
            name="email"
            value={loginInfo.email}
            placeholder="이메일을 입력하세요"
            required
            onChange={handleChange}
          />
          <input
            type="password"
            name="password"
            value={loginInfo.password}
            placeholder="비밀번호를 입력하세요"
            required
            onChange={handleChange}
          />
        </div>
        <Button
          text="로그인하기"
          disabled={Object.values(loginInfo).some((info) => !info)}
          type="submit"
          isLoading={login.isLoading}
        />
      </form>
      <Link to="/signup" className="border-b border-gray-950">
        회원가입
      </Link>
    </section>
  );
}
