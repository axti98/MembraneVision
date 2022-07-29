//
// Created by User on 03.06.2022.
//

#ifndef CMD_TOOL_SRC_SEGMENTATIONHANDLER_H
#define CMD_TOOL_SRC_SEGMENTATIONHANDLER_H

#include <iostream>

#include <opencv2/core.hpp>
#include <opencv2/imgcodecs.hpp>
#include <opencv2/imgproc.hpp>
#include <opencv2/highgui.hpp>

#include "Const.h"

#include <string>

#include "omp.h"

#define THRESH_MAX 1
#define THRESH_VAL 0

#define IS_VOXEL 1
#define IS_NOT_VOXEL -1

#define NEIGHBOUR_MULTIPLIER(x) 2*x

#define LEFT_INDEX(x) 2*x
#define RIGHT_INDEX(x) 2*x + 1

class SegmentationHandler{
public:
    explicit SegmentationHandler(std::string* AImage_paths, int AImage_count);
    cv::Mat *segmentation();
    int **neighbouring(const cv::Mat& AInputMat);
    int **neighbour_list(const int** numbering, const int rows, const int cols);
private:
    int image_count;
    std::string* image_paths;
};

#endif //CMD_TOOL_SRC_SEGMENTATIONHANDLER_H
