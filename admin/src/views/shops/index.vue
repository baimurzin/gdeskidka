<template>
  <div class="dashboard-editor-container">
    <el-row class="panel-group" :gutter="40">

      <!--As a button to create new shop -->
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class='card-panel' @click="dialogTableVisible = true">
          <div class="card-panel-icon-wrapper icon-people">
            <svg-icon icon-class="plus" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">Add new shop</div>
            <count-to class="card-panel-num" :startVal="0" :endVal="102400" :duration="260"></count-to>
          </div>
        </div>
      </el-col>


    </el-row>
    <el-dialog v-el-drag-dialog  title="Add new shop" :visible.sync="dialogTableVisible">
      <el-form autoComplete="on" :model="shopForm" label-position="left">
        <switch-shop-button v-model="shopForm.shopType"/>

        <el-form-item prop="name">
          <el-input :span="6" :offset="5" label="Shop name:" placeholder="Shop name" v-model="shopForm.name" type="text" clearable size="small"/>
        </el-form-item>

        <el-upload
          class="upload-demo"
          drag
          action="https://jsonplaceholder.typicode.com/posts/"
          :file-list="shopForm.photo"
          :limit="1"
          :on-exceed="handleExceed"
          multiple>
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">Drop file here or <em>click to upload</em></div>
          <div class="el-upload__tip" slot="tip">jpg/png files with a size less than 500kb</div>
        </el-upload>

        <el-form-item v-if="shopForm.shopType === 'Online'" prop="address">
          <el-input placeholder="Please input" v-model="shopForm.address">
            <template slot="prepend">Http://</template>
          </el-input>
        </el-form-item>
        <el-form-item v-else prop="address">
          <el-input :span="6" :offset="5" label="Shop name:" placeholder="Shop url" v-model="shopForm.name" type="text" clearable size="small"/>
        </el-form-item>

        <el-button type="success">Success</el-button>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
  import CountTo from 'vue-count-to'
  import elDragDialog from '@/directive/el-dragDialog' // base on element-ui
  import SwitchShopButton from './components/SwitchShopType'
  import { Message } from 'element-ui'

  export default {
    name: 'shops',
    directives: { elDragDialog },
    components: {
      CountTo,
      SwitchShopButton
    },
    data() {
      return {
        dialogTableVisible: false,
        shopForm: {
          shopType: 'Online',
          name: '',
          photo: [],
          address: ''
        }
      }
    },
    methods: {
      handleExceed(file, fileList) {
        Message({
          message: 'You can upload only one photo.',
          type: 'error',
          duration: 5 * 1000
        })
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .dashboard-editor-container {
    padding: 32px;
    background-color: rgb(240, 242, 245);
    .chart-wrapper {
      background: #fff;
      padding: 16px 16px 0;
      margin-bottom: 32px;
    }
  }
  .panel-group {
    margin-top: 18px;
    .card-panel-col{
      margin-bottom: 32px;
    }
    .card-panel {
      height: 108px;
      cursor: pointer;
      font-size: 12px;
      position: relative;
      overflow: hidden;
      color: #666;
      background: #fff;
      box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
      border-color: rgba(0, 0, 0, .05);
      &:hover {
        .card-panel-icon-wrapper {
          color: #fff;
        }
        .icon-people {
          background: #40c9c6;
        }
        .icon-message {
          background: #36a3f7;
        }
        .icon-money {
          background: #f4516c;
        }
        .icon-shoppingCard {
          background: #34bfa3
        }
      }
      .icon-people {
        color: #40c9c6;
      }
      .icon-message {
        color: #36a3f7;
      }
      .icon-money {
        color: #f4516c;
      }
      .icon-shoppingCard {
        color: #34bfa3
      }
      .card-panel-icon-wrapper {
        float: left;
        margin: 14px 0 0 14px;
        padding: 16px;
        transition: all 0.38s ease-out;
        border-radius: 6px;
      }
      .card-panel-icon {
        float: left;
        font-size: 48px;
      }
      .card-panel-description {
        float: right;
        font-weight: bold;
        margin: 26px;
        margin-left: 0px;
        .card-panel-text {
          line-height: 18px;
          color: rgba(0, 0, 0, 0.45);
          font-size: 16px;
          margin-bottom: 12px;
        }
        .card-panel-num {
          font-size: 20px;
        }
      }
    }
  }
</style>
