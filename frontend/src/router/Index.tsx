import React from 'react'
import { Route, Routes } from 'react-router'
import HomeScreen from '../screens/HomeScreen'
import LoginScreen from '../screens/LoginScreen'
import SignupScreen from '../screens/SignupScreen'
import CheckTokenComp from '../components/CheckTokenComp'

const Index:React.FC = () => {
  return (
   <Routes>
    <Route path="/" element={<CheckTokenComp><HomeScreen /></CheckTokenComp>} />
     <Route path="/Login" element={<LoginScreen />} />
     <Route path="/signup" element={<SignupScreen />} />
   </Routes>
  )
}


export default Index