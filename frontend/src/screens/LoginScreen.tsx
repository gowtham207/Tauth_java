import React, { useCallback, useState } from "react";
import CustomFormInput from "../components/CustomFormInput";
import type { loginFormTypes } from "../types/formtypes";
import Logo from "../components/Logo";
import { Link } from "react-router";

const LoginScreen: React.FunctionComponent = () => {

  const [formValue,setFormValue] = useState<loginFormTypes>({
    email:"",
    password:""
  })

  const onSubmit = useCallback((e: React.FormEvent) => {
    e.preventDefault();
    console.log("value");
  }, []);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormValue((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-indigo-500 via-purple-500 to-pink-500 p-4">
      <div className="bg-white rounded-2xl shadow-2xl p-8 w-full max-w-md flex flex-col justify-center">
        {/* Header */}
        <div className="text-center mb-6">
         <Logo />
          <p className="text-gray-500 mt-1 text-sm">
            Secure Access Made Simple
          </p>
        </div>

        {/* Description */}
        <p className="text-gray-600 text-center text-sm mb-6 leading-relaxed">
          A flexible authentication service built for developers. <br />
          Easily integrate login, signup, and user management into any app with
          just a few lines of code.
        </p>

        {/* Login Form */}
        <form className="flex flex-col gap-4" onSubmit={onSubmit}>
          <CustomFormInput
            title="Email"
            name="email"
            placeholder="you@example.com"
            required={true}
            type="email"
            onchange={handleChange}
            value={formValue.email}
          />
          <CustomFormInput
            title="Password"
            name="password"
            placeholder="••••••••"
            required={true}
            type="password"
            onchange={handleChange}
            value={formValue.password}
          />

          <button
            type="submit"
            className="mt-4 bg-indigo-600 text-white py-2 rounded-lg hover:bg-indigo-700 transition duration-200 font-medium"
          >
            Login
          </button>
        </form>

        {/* Footer Links */}
        <div className="text-center text-sm text-gray-500 mt-6">
          Don’t have an account?{" "}
          <Link to={"/signup"} className="text-indigo-600 hover:underline">
            Sign up
          </Link>
        </div>
      </div>
    </div>
  );
};

export default LoginScreen;
