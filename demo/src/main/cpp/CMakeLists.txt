cmake_minimum_required(VERSION 3.22.1)
project("demo")

add_library(demo_a SHARED demo_a.cpp)
target_link_libraries(demo_a log)

add_library(demo_b SHARED demo_b.cpp)
target_link_libraries(demo_b log)
