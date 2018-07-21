<template>
  <div class="dashboard-editor-container">
    <el-row class="panel-group" :gutter="40">

      <!--As a button to create new shop -->
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class='card-panel' style="height: 180px;" @click="dialogTableVisible = true">
          <div class="card-panel-icon-wrapper icon-people">
            <svg-icon icon-class="plus" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">Add shop</div>
            <count-to class="card-panel-num" :startVal="0" :endVal="102400" :duration="2600"></count-to>
          </div>
        </div>
      </el-col>


    </el-row>

    <el-row class="panel-group" :gutter="40">

      <!--As a button to create new shop -->
      <router-link v-for="shop in shops" :key="shop.id" :item="shop" :to="{ name: 'Shop', params: {id: shop.id} }">
        <el-col  :xs="12" :sm="12" :lg="6" class="card-panel-col">
          <div class='card-panel'>
            <div class="card-panel-icon-wrapper icon-people">
              <svg-icon icon-class="plus" class-name="card-panel-icon" />
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text">{{shop.name}}</div>
              <count-to class="card-panel-num" :startVal="0" :endVal="102400" :duration="260"></count-to>
            </div>
          </div>
        </el-col>
      </router-link>


    </el-row>

    <el-dialog v-el-drag-dialog  title="Add new shop" :visible.sync="dialogTableVisible">
      <el-form autoComplete="on" :model="shopForm" :ref="shopForm" label-position="left">

        <!--<switch-shop-button :model="shopForm.shopType"/>-->


        <el-form-item prop="type">
          <div>
            Shop type:
            <el-radio-group v-model="shopForm.shopType">
              <el-radio-button label="Online"></el-radio-button>
              <el-radio-button label="Offline"></el-radio-button>
            </el-radio-group>
          </div>
        </el-form-item>

        <el-form-item label="Shop name" prop="name">
          <el-input :ref="shopForm.name" :span="6" :offset="5" label="Shop name:" placeholder="Shop name" v-model="shopForm.name" type="text" clearable size="small"/>
        </el-form-item>

        <el-form-item prop="name" label="Shop photo">
          <el-upload
            class="upload-demo"
            drag
            :action="apiUrl + '/storage/uploadFile'"
            :file-list="shopForm.photos"
            :limit="1"
            :on-exceed="handleExceed"
            :on-success="handleSuccess"
            >
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">Drop file here or <em>click to upload</em></div>
            <!--<div class="el-upload__tip" slot="tip">jpg/png files with a size less than 500kb</div>-->
          </el-upload>
        </el-form-item>



        <el-form-item label="Shop address" v-if="shopForm.shopType === 'Online'" prop="address">
          <el-input placeholder="Shop url" v-model="shopForm.address">
            <template slot="prepend">Http://</template>
          </el-input>
        </el-form-item>
        <el-form-item label="Shop address" v-else prop="address">
          <el-input :span="6" :offset="5" label="Shop name:" placeholder="Shop url" v-model="shopForm.address" type="text" clearable size="small"/>
        </el-form-item>

        <el-button :loading="loading" @click.native.prevent="createShop" type="success">Create</el-button>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
  import CountTo from 'vue-count-to'
  import elDragDialog from '@/directive/el-dragDialog' // base on element-ui
  import SwitchShopButton from './components/SwitchShopType'
  import { Message } from 'element-ui'
  import { mapGetters } from 'vuex'

  export default {
    name: 'shops',
    directives: { elDragDialog },
    components: {
      CountTo,
      SwitchShopButton
    },
    data() {
      return {
        apiUrl: process.env.BASE_API,
        loading: false,
        dialogTableVisible: false,
        shopForm: {
          shopType: 'Online',
          name: '',
          photos: [],
          address: '',
          photo: ''
        }
      }
    },
    computed: {
      ...mapGetters([
        'shops'
      ])
    },
    methods: {
      handleExceed(file, fileList) {
        Message({
          message: 'You can upload only one photo.',
          type: 'error',
          duration: 5 * 1000
        })
      },
      handleSuccess(resp, file, fileList) {
        this.shopForm.photo = resp
      },
      createShop() {
        console.log('before create api shop', this.shopForm)
        this.$store.dispatch('CreateShop', this.shopForm).then(() => {
          this.loading = false
          this.dialogTableVisible = false
        }).catch(() => {
          this.loading = false
          console.log('bad')
        })
      }
    },
    created() {
      this.$store.dispatch('GetShops').then(() => {
        this.loading = false
        this.dialogTableVisible = false
      }).catch(() => {
        this.loading = false
        console.log('bad')
      })
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
